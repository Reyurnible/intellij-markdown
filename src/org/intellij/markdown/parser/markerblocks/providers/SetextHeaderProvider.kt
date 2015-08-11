package org.intellij.markdown.parser.markerblocks.providers

import org.intellij.markdown.parser.LookaheadText
import org.intellij.markdown.parser.MarkerProcessor
import org.intellij.markdown.parser.ProductionHolder
import org.intellij.markdown.parser.constraints.MarkdownConstraints
import org.intellij.markdown.parser.markerblocks.MarkerBlock
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider
import org.intellij.markdown.parser.markerblocks.impl.SetextHeaderMarkerBlock
import kotlin.text.Regex

public class SetextHeaderProvider : MarkerBlockProvider<MarkerProcessor.StateInfo> {
    override fun createMarkerBlocks(pos: LookaheadText.Position,
                                   productionHolder: ProductionHolder,
                                   stateInfo: MarkerProcessor.StateInfo): List<MarkerBlock> {
        if (stateInfo.paragraphBlock != null) {
            return emptyList()
        }
        if (pos.offsetInCurrentLine == 0 && pos.nextLine?.matches(REGEX) == true) {
            return listOf(SetextHeaderMarkerBlock(stateInfo.currentConstraints, productionHolder))
        } else {
            return emptyList()
        }
    }

    override fun interruptsParagraph(pos: LookaheadText.Position, constraints: MarkdownConstraints): Boolean {
        return false
    }

    companion object {
        val REGEX: Regex = Regex("^ {0,3}(\\-+|=+) *$")
    }
}